let loaded = 0, toLoad = 2;

function loadStep() {
    loaded++;
    if (loaded >= toLoad) {
        setTimeout(function () {
            $("#app").attr('loaded', 'true');
            $(".determinate").show("slide", { direction: "left" }, 1000);
            $(".tabs > .tab:first-child > a").click();
        }, 1000);
    }
}

application = new Vue({
    el: '#app',
    data: {
        user: {
            name:'', picture:''
        }
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

var href = window.location.href;
var token = href.substring(href.indexOf("=")+1,href.indexOf("&"));

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
// Listening for the authenticated event
lock.on("authenticated", function(authResult) {
    // Use the token in authResult to getUserInfo() and save it to localStorage
    lock.getUserInfo(authResult.accessToken, function(error, profile) {
        if (error) {
            // Handle error
            return;
        }

        localStorage.setItem('accessToken', authResult.accessToken);
        localStorage.setItem('profile', JSON.stringify(profile));

        application.user = {
            picture: profile.picture,
            name: profile.name
        };
        application.$forceUpdate();
        loadStep();
    });
});

if (token.length != 32 && (token = localStorage.getItem('accessToken'),
    !token || token.length != 32)) {
    lock.show();
} else {
    document.getElementById('loading').style.display = null;
    if (profile = JSON.parse(localStorage.getItem('profile'))) {
        application.user = {
            picture: profile.picture,
            name: profile.name
        };
        application.$forceUpdate();
    }
    loadStep();
}

function logout () {
    lock.logout();
    localStorage.removeItem( 'accessToken' );
    localStorage.removeItem( 'profile' );
}

$(".button-collapse").sideNav();