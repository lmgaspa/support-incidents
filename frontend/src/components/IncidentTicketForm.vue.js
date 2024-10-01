import { defineComponent, ref } from 'vue';
export default defineComponent({
    name: 'TicketForm',
    setup() {
        const ticket = ref({
            user: '',
            company: '',
            emailTo: '',
            problem: '',
            description: '',
            priority: 'medium',
        });
        const handleSubmit = async () => {
            if (validateForm()) {
                try {
                    const response = await fetch('https://ticket-incident.herokuapp.com/send-ticket', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(ticket.value),
                    });
                    if (response.ok) {
                        alert('Ticket sent successfully!');
                    }
                    else {
                        alert('Error sending ticket!');
                    }
                }
                catch (error) {
                    console.error('Error:', error);
                }
            }
        };
        const validateEmail = (email) => {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return emailRegex.test(email);
        };
        const validateForm = () => {
            for (const key in ticket.value) {
                if (ticket.value[key] === '') {
                    alert(`${key} is required`);
                    return false;
                }
            }
            if (!validateEmail(ticket.value.emailTo)) {
                alert('Please enter a valid email address');
                return false;
            }
            return true;
        };
        return {
            ticket,
            handleSubmit,
        };
    },
});
;
function __VLS_template() {
    const __VLS_ctx = {};
    const __VLS_localComponents = {
        ...{},
        ...{},
        ...__VLS_ctx,
    };
    let __VLS_components;
    const __VLS_localDirectives = {
        ...{},
        ...__VLS_ctx,
    };
    let __VLS_directives;
    let __VLS_styleScopedClasses;
    let __VLS_resolvedLocalAndGlobalComponents;
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("container-fluid vh-100 d-flex justify-content-center align-items-center") }, ...{ style: ({}) }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.form, __VLS_intrinsicElements.form)({ ...{ onSubmit: (__VLS_ctx.handleSubmit) }, ...{ class: ("p-4 border rounded") }, ...{ style: ({}) }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.h2, __VLS_intrinsicElements.h2)({ ...{ class: ("mb-4 text-center") }, ...{ style: ({}) }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("form-group mb-3") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.label, __VLS_intrinsicElements.label)({ for: ("user"), ...{ class: ("form-label") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.input)({ type: ("text"), ...{ class: ("form-control") }, id: ("user"), value: ((__VLS_ctx.ticket.user)), required: (true), });
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("form-group mb-3") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.label, __VLS_intrinsicElements.label)({ for: ("company"), ...{ class: ("form-label") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.input)({ type: ("text"), ...{ class: ("form-control") }, id: ("company"), value: ((__VLS_ctx.ticket.company)), required: (true), });
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("form-group mb-3") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.label, __VLS_intrinsicElements.label)({ for: ("emailTo"), ...{ class: ("form-label") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.input)({ type: ("email"), ...{ class: ("form-control") }, id: ("emailTo"), required: (true), });
    (__VLS_ctx.ticket.emailTo);
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("form-group mb-3") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.label, __VLS_intrinsicElements.label)({ for: ("problem"), ...{ class: ("form-label") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.input)({ type: ("text"), ...{ class: ("form-control") }, id: ("problem"), value: ((__VLS_ctx.ticket.problem)), required: (true), });
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("form-group mb-3") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.label, __VLS_intrinsicElements.label)({ for: ("description"), ...{ class: ("form-label") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.textarea, __VLS_intrinsicElements.textarea)({ ...{ class: ("form-control") }, id: ("description"), value: ((__VLS_ctx.ticket.description)), rows: ("3"), required: (true), });
    __VLS_elementAsFunction(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({ ...{ class: ("form-group mb-3") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.label, __VLS_intrinsicElements.label)({ for: ("priority"), ...{ class: ("form-label") }, });
    __VLS_elementAsFunction(__VLS_intrinsicElements.select, __VLS_intrinsicElements.select)({ ...{ class: ("form-control") }, id: ("priority"), value: ((__VLS_ctx.ticket.priority)), required: (true), });
    __VLS_elementAsFunction(__VLS_intrinsicElements.option, __VLS_intrinsicElements.option)({ value: ("high"), });
    __VLS_elementAsFunction(__VLS_intrinsicElements.option, __VLS_intrinsicElements.option)({ value: ("medium"), });
    __VLS_elementAsFunction(__VLS_intrinsicElements.option, __VLS_intrinsicElements.option)({ value: ("low"), });
    __VLS_elementAsFunction(__VLS_intrinsicElements.button, __VLS_intrinsicElements.button)({ type: ("submit"), ...{ class: ("btn btn-lg w-100") }, ...{ style: ({}) }, });
    __VLS_styleScopedClasses['container-fluid'];
    __VLS_styleScopedClasses['vh-100'];
    __VLS_styleScopedClasses['d-flex'];
    __VLS_styleScopedClasses['justify-content-center'];
    __VLS_styleScopedClasses['align-items-center'];
    __VLS_styleScopedClasses['p-4'];
    __VLS_styleScopedClasses['border'];
    __VLS_styleScopedClasses['rounded'];
    __VLS_styleScopedClasses['mb-4'];
    __VLS_styleScopedClasses['text-center'];
    __VLS_styleScopedClasses['form-group'];
    __VLS_styleScopedClasses['mb-3'];
    __VLS_styleScopedClasses['form-label'];
    __VLS_styleScopedClasses['form-control'];
    __VLS_styleScopedClasses['form-group'];
    __VLS_styleScopedClasses['mb-3'];
    __VLS_styleScopedClasses['form-label'];
    __VLS_styleScopedClasses['form-control'];
    __VLS_styleScopedClasses['form-group'];
    __VLS_styleScopedClasses['mb-3'];
    __VLS_styleScopedClasses['form-label'];
    __VLS_styleScopedClasses['form-control'];
    __VLS_styleScopedClasses['form-group'];
    __VLS_styleScopedClasses['mb-3'];
    __VLS_styleScopedClasses['form-label'];
    __VLS_styleScopedClasses['form-control'];
    __VLS_styleScopedClasses['form-group'];
    __VLS_styleScopedClasses['mb-3'];
    __VLS_styleScopedClasses['form-label'];
    __VLS_styleScopedClasses['form-control'];
    __VLS_styleScopedClasses['form-group'];
    __VLS_styleScopedClasses['mb-3'];
    __VLS_styleScopedClasses['form-label'];
    __VLS_styleScopedClasses['form-control'];
    __VLS_styleScopedClasses['btn'];
    __VLS_styleScopedClasses['btn-lg'];
    __VLS_styleScopedClasses['w-100'];
    var __VLS_slots;
    var __VLS_inheritedAttrs;
    const __VLS_refs = {};
    var $refs;
    return {
        slots: __VLS_slots,
        refs: $refs,
        attrs: {},
    };
}
;
let __VLS_self;
