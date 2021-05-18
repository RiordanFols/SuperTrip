let ticketSearchForm = new Vue({
    el: '#ticketSearchForm',
    data: {
        fd: frontendData.formData,
        error: frontendData.error,
    },
    template:
        '<form method="post" action="/ticket/search">' +

            '<div class="user-info-form">' +
                '<div class="user-info-form-caption">Already have tickets, but lost them? Enter passenger data to find your tickets</div>' +

                '<div class="error" v-if="error !== null">{{ error }}</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Name</span>' +
                    '<input  class="user-info-input" type="text" maxlength="25" name="name" :value="fd.name" required autofocus>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Surname</span>' +
                    '<input  class="user-info-input" type="text" maxlength="25" name="surname" :value="fd.surname" required>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Middle name</span>' +
                    '<input  class="user-info-input" type="text" maxlength="25" :value="fd.middleName" name="middleName">' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Passport id</span>' +
                    '<input  class="user-info-input" type="text" minlength="6" maxlength="6" name="passportId" :value="fd.passportId" required>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Passport series</span>' +
                    '<input  class="user-info-input" type="text" minlength="4" maxlength="4" name="passportSeries" :value="fd.passportSeries" required>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<input class="user-info-btn" value="Ok" type="submit">' +
                '</div>' +
            '</div>' +
        '</form>'
});