let loaded = 0, toLoad = 2;

function loadStep() {
    loaded++;
    if (loaded >= toLoad) {
        $("#app").attr('loaded', 'true');
        $(".determinate").show("slide", { direction: "left" }, 100);
        $(".tabs > .tab:first-child > a").click();
    }
}

application = new Vue({
    el: '#app',
    data: {
        user: {
            name:'', picture:''
        },
        token: "temp"
    },
    mounted() {
        this.$http.get('/api/v1/getUserStatus').then(
            response => {
                application.text = response.body;
                loadStep();
            },
            response => {
                console.log("error");
            }
        )
    }
});

href = window.location.href;
token = href.substring(href.indexOf("=")+1,href.indexOf("&"));

// Initializing our Auth0Lock
var lock = new Auth0Lock(
    '3a0iMYzlXuCq5uQJJdAjKekCdF_dWfZe',
    'frequent-flyer.eu.auth0.com',
    {
        closable: false,
        languageDictionary: {
            title: "FrequentFlyer"
        },
        theme: {
            logo: 'http://www.primclub.ru/images/824400_airport_512x512.png',
            primaryColor: '#FF6262'
        }
    }
);

getUserData = function(profile) {
    application.user = {
        picture: profile.picture,
        name: profile.name,
        hasPersonalData: (profile.given_name !== null),
        address: profile.address,
        userId: profile.sub
    };
    application.$forceUpdate();
    loadStep();
};

function patch_metadata(string) {
    profile = JSON.parse(localStorage.getItem('profile'));
    patch = JSON.parse(string);

    for(entry in patch["user_metadata"]) {
        profile[entry] = patch["user_metadata"][entry];
    }

    localStorage.setItem('profile', JSON.stringify(profile));
}

// Listening for the authenticated event
lock.on("authenticated", function(authResult) {
    lock.getUserInfo(authResult.accessToken, function(error, profile) {
        if (error) {
            // Handle error
            return;
        }

        application.token = authResult.accessToken;

        for(entry in profile["http://localhost:8080/user_metadata"]) {
            profile[entry] = profile["http://localhost:8080/user_metadata"][entry];
        }
        profile["http://localhost:8080/user_metadata"] = null;

        localStorage.setItem('accessToken', authResult.accessToken);
        localStorage.setItem('profile', JSON.stringify(profile));

        getUserData(profile);
    });
});

if (token.length !== 32) {
    token = localStorage.getItem('accessToken');
}
if (!token || token.length !== 32) {
    lock.show();
} else {
    application.token = token;
    profile = JSON.parse(localStorage.getItem('profile'));
    if (profile) {
        document.getElementById('loading').style.display = null;
        getUserData(profile);
    } /*else {
        lock.show();
    }*/
}

function logout () {
    lock.logout();
    localStorage.removeItem( 'accessToken' );
    localStorage.removeItem( 'profile' );
}

$(".button-collapse").sideNav();

$('#profileForm').submit(function(e) {
    console.log($("#profileForm").serialize());
    $.ajax({
        type: "POST",
        url: "./api/v1/updateProfile",
        data: $("#profileForm").serialize(), // serializes the form's elements.
        success: function(data)
        {
            patch_metadata(data);
            Materialize.toast('Successfully updated your profile!', 4000);
        },
        error: function (error) {
            Materialize.toast('Error! '+error, 4000);
        }
    });
    e.preventDefault(); // avoid to execute the actual submit of the form.
});