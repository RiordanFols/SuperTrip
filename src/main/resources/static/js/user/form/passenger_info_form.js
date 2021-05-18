let passengerInfoForm = new Vue({
    el: '#passengerInfoForm',
    data: {
        fd: frontendData.formData,
        solutionId: frontendData.solutionId,
        error: frontendData.error
    },
    template:
        '<form method="post" v-bind:href="\'/ticket/assemble/\' + solutionId">' +

            '<div class="user-info-form">' +
                '<div class="user-info-form-caption">Enter passenger data</div>' +

                '<div class="error" v-if="error !== null">{{ error }}</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Name</span>' +
                    '<input class="user-info-input" type="text" maxlength="25" name="name" :value="fd.name" required autofocus/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Surname</span>' +
                    '<input class="user-info-input" type="text" maxlength="25" name="surname" :value="fd.surname" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Middle name</span>' +
                    '<input class="user-info-input" type="text" maxlength="25" :value="fd.middleName" name="middleName"/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Passport id</span>' +
                    '<input class="user-info-input" type="text" maxlength="6" minlength="6" name="passportId" :value="fd.passportId" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<span class="user-info-label">Passport series</span>' +
                    '<input class="user-info-input" type="text" maxlength="4" minlength="4" name="passportSeries" :value="fd.passportSeries" required/>' +
                '</div>' +

                '<div class="user-info-input-line">' +
                    '<input class="user-info-btn" value="Go to payment" type="submit"/>' +
                '</div>' +
            '</div>' +

        '</form>'
});