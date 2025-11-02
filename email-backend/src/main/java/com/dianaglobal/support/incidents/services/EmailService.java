package com.dianaglobal.support.incidents.services;

import com.dianaglobal.support.incidents.enums.StatusEmail;
import com.dianaglobal.support.incidents.models.EmailModel;
import com.dianaglobal.support.incidents.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

/**
 * EmailService — responsável por:
 * 1) Persistir o ticket no Mongo (EmailRepository)
 * 2) Enviar o e-mail usando JavaMailSender
 *
 * OCP/SOLID:
 * - buildHtmlTemplate(...) isolado: facilita variações de layout sem mudar regras de envio.
 * - Helpers puros (nz/esc) sem efeitos colaterais.
 * - Dependências injetadas por construtor.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    /** Nome exibido no cabeçalho e rodapé */
    @Value("${application.brand.name:AndesCore Support}")
    private String brandName;

    /** Endereço padrão do FROM (opcional). Se vazio, usa MAIL_USERNAME do ambiente (compatível com Gmail) */
    @Value("${mail.from:}")
    private String configuredFrom;

    /** URL do logo (externo, evita anexo) */
    @Value("${mail.logo.url:https://www.andescoresoftware.com.br/logo-512.png}")
    private String logoUrl;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    // -------------------- Queries --------------------

    public List<EmailModel> getAllEmails() {
        return emailRepository.findAll();
    }

    public List<EmailModel> findByEmailTo(String emailTo) {
        return emailRepository.findByEmailTo(emailTo);
    }

    // -------------------- Command --------------------

    /**
     * Persiste o ticket e envia o e-mail com template AndesCore (footer sem ícone).
     * Sempre salva o estado final (SENT/ERROR) no Mongo.
     */
    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDataEmail(LocalDateTime.now());

        final String html = buildHtmlTemplate(emailModel);

        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, /* multipart */ false, StandardCharsets.UTF_8.name());

            // FROM seguro: usa mail.from ou MAIL_USERNAME; evita spoof do remetente
            String envFrom = System.getenv("MAIL_USERNAME");
            String fromToUse = !isBlank(configuredFrom) ? configuredFrom : (!isBlank(envFrom) ? envFrom : null);
            if (fromToUse != null) {
                helper.setFrom(fromToUse, brandName);
            }

            // Reply-To aponta para quem abriu o ticket
            if (!isBlank(emailModel.getEmailFrom())) {
                String personal = !isBlank(emailModel.getOwnerRef()) ? emailModel.getOwnerRef() : emailModel.getEmailFrom();
                helper.setReplyTo(emailModel.getEmailFrom(), personal);
            }

            helper.setTo(emailModel.getEmailTo());
            helper.setSubject(emailModel.getSubject());
            helper.setText(html, true);

            emailSender.send(mimeMessage);
            emailModel.setStatusEmail(StatusEmail.SENT);
            logger.info("MAIL enviado OK -> {}", emailModel.getEmailTo());

        } catch (MailException | MessagingException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            logger.error("Error sending email: {}", e.getMessage(), e);
        } finally {
            // Sempre registra o resultado (SENT/ERROR) no histórico
            return emailRepository.save(emailModel);
        }
    }

    // -------------------- Template (OCP) --------------------

    /**
     * Template AndesCore (sem ícone/emoji no rodapé).
     * Cabeçalho com gradiente + logo por URL.
     */
    private String buildHtmlTemplate(EmailModel m) {
        String subtitle = "Novo ticket de suporte";
        String priorityPill = """
                <span style='display:inline-block;padding:2px 8px;border-radius:999px;background:#eef2ff;color:#1d4ed8;font-size:12px;'>%s</span>
                """.formatted(esc(nz(m.getPriority())));

        return """
        <!doctype html>
        <html lang='pt-BR'>
        <head>
          <meta charset='UTF-8'>
          <title>%s</title>
        </head>
        <body style='margin:0;padding:0;background:#f4f6f8;font-family:Arial,Helvetica,sans-serif;color:#111;'>
          <table role='presentation' width='100%%' cellspacing='0' cellpadding='0' style='background:#f4f6f8;padding:24px 0;'>
            <tr><td align='center'>
              <table role='presentation' width='640' cellspacing='0' cellpadding='0' style='background:#fff;border-radius:12px;overflow:hidden;box-shadow:0 2px 8px rgba(0,0,0,.06);'>

                <!-- HEADER com LOGO -->
                <tr><td style='background:linear-gradient(135deg,#0a2239,#0e4b68);padding:14px 20px;color:#fff;'>
                  <table width='100%%' cellspacing='0' cellpadding='0' style='border-collapse:collapse'>
                    <tr>
                      <td style='width:64px;vertical-align:middle;'>
                        <img src='%s' alt='%s' width='56' style='display:block;border-radius:6px;'>
                      </td>
                      <td style='text-align:right;vertical-align:middle;'>
                        <div style='font-weight:700;font-size:18px;line-height:1;'>%s</div>
                        <div style='height:6px;line-height:6px;font-size:0;'>&nbsp;</div>
                        <div style='opacity:.9;font-size:12px;line-height:1.2;'>%s</div>
                      </td>
                    </tr>
                  </table>
                </td></tr>

                <!-- CORPO -->
                <tr><td style='padding:24px;'>
                  <table role='presentation' width='100%%' cellspacing='0' cellpadding='0' style='border-collapse:separate;border-spacing:0 10px;'>
                    <tr><td style='width:180px;color:#555;font-weight:bold;'>From</td><td>%s</td></tr>
                    <tr><td style='width:180px;color:#555;font-weight:bold;'>User</td><td>%s</td></tr>
                    <tr><td style='width:180px;color:#555;font-weight:bold;'>Company</td><td>%s</td></tr>
                    <tr><td style='width:180px;color:#555;font-weight:bold;'>Problem</td><td style='color:#c1121f;'>%s</td></tr>
                    <tr><td style='width:180px;color:#555;font-weight:bold;'>Priority</td><td>%s</td></tr>
                  </table>

                  <div style='margin:18px 0 8px 0;color:#555;font-weight:bold;'>Description</div>
                  <div style='padding:14px 16px;border:1px solid #e5e7eb;border-radius:6px;background:#fafafa;white-space:pre-line;'>%s</div>
                </td></tr>

                <!-- FOOTER (sem ícones) -->
                <tr><td style='background:linear-gradient(135deg,#0a2239,#0e4b68);color:#fff;
                                padding:6px 18px;text-align:center;font-size:14px;line-height:1;'>
                  <span style="vertical-align:middle;">© %d · Powered by <strong>AndesCoreSoftware</strong></span>
                </td></tr>

              </table>
            </td></tr>
          </table>
        </body>
        </html>
        """.formatted(
                esc(nz(m.getSubject())),
                esc(logoUrl),
                esc(brandName),
                esc(brandName),
                esc(subtitle),
                esc(nz(m.getEmailFrom())),
                esc(nz(m.getOwnerRef())),
                esc(nz(m.getCompany())),
                esc(nz(m.getProblem())),
                priorityPill,
                esc(nz(m.getText())),
                Year.now().getValue()
        );
    }

    // -------------------- Helpers (puros) --------------------

    private static String nz(String s) { return s == null ? "" : s; }

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    private static String esc(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
