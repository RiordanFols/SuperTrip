let header = new Vue({
    el: '#header',
    data: {
        authUser: frontendData.authUser,
    },
    template:
    '<div class="header">' +
        '<div class="header-content">' +
            '<a href="/"><div class="header-app-name">Super trip</div></a>' +
            '<div class="header-middle">' +
                '<div></div>' +
                '<div></div>' +
                '<div></div>' +
            '</div>' +
            '<a v-if="authUser !== null" href="/profile">' +
                '<div class="header-profile">' +
                    '<img class="header-profile-img" src="/img/avatar.png"  alt=""/>' +
                    '<div class="header-profile-name">{{ authUser.name }}</div>' +
                '</div>' +
            '</a>' +

            '<a class="header-auth" v-if="authUser === null" href="/login">Login</a>' +
            '<a class="header-auth" v-else href="/logout">Logout</a>' +
        '</div>' +
    '</div>'
});