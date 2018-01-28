let loaded = 0, toLoad = 3;

function loadStep() {
    loaded++;

    if (loaded === 1) {
        application.$http.get('/api/v1/getUserStatus?userId='+application.user.userId).then(
            response => {
                console.log(response.body);
                loadStep();
            },
            response => {
                console.log("error");
            }
        );

        application.$http.get('/api/v1/getTickets?email='+application.user.email).then(
            response => {
                application.tickets = response.body;
                loadStep();
            },
            response => {
                console.log("error");
            }
        );
    }

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
        tickets: ''
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
        miles: profile.miles?profile.miles:0,
        email: profile.email,
        picture: profile.picture,
        name: profile.name,
        hasPersonalData: (profile.given_name !== null),
        address: profile.address,
        userId: profile.sub
    };
    if (loaded < toLoad) {
        application.$forceUpdate();
        loadStep();
    }
};

function patch_metadata(string) {
    profile = JSON.parse(localStorage.getItem('profile'));
    patch = JSON.parse(string);

    for(entry in patch["user_metadata"]) {
        profile[entry] = patch["user_metadata"][entry];
    }

    localStorage.setItem('profile', JSON.stringify(profile));
    getUserData(profile);
}

// Listening for the authenticated event
lock.on("authenticated", function(authResult) {
    lock.getUserInfo(authResult.accessToken, function(error, profile) {
        if (error) {
            // Handle error
            return;
        }

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
    e.preventDefault();
}).validate({
    rules: {
        name: {
            required: true,
            correctName: true,
        }
    },
    messages: {
        name: "Please enter your given and family name"
    },
    errorElement : 'div',
    errorPlacement: function(error, element) {
        let placement = $(element).data('error');
        if (placement) {
            $(placement).append(error);
        } else {
            error.insertAfter(element);
        }
    },

    highlight: function(element) {
        $(element).removeClass('ok');
    },
    unhighlight: function(element) {
        $(element).addClass('ok');
    },

    submitHandler: function(form){
        $.ajax({
            type: "POST",
            url: "./api/v1/updateProfile",
            data: $(form).serialize(), // serializes the form's elements.
            success: function(data)
            {
                patch_metadata(data);
                Materialize.toast('Successfully updated your profile!', 4000);
            },
            error: function (error) {
                Materialize.toast('Error! '+error, 4000);
            }
        });
    }
});

$("#resetPass").click(function (e) {
    $.ajax({
        type: "GET",
        url: "https://frequent-flyer.eu.auth0.com/dbconnections/change_password?"+
            "connection=Username-Password-Authentication&email="+application.user.email,
        success: function(data)
        {
            Materialize.toast(data, 4000);
        },
        error: function (error) {
            Materialize.toast('Error! '+error, 4000);
        }
    });
    e.preventDefault();
});

citySources = {
    "Cracow" : "KRK",
    "New York City": "NYC",
    "Sao Paulo": "GRU",
    "Chicago": "ORD",
    "San Diego": "SAN",
    "Atlanta": "ATL",
    "New Orleans": "MSY",
    "Brasilia": "BSB",
    "Los Angeles": "LAX",
    "Curitiba": "CWB",
    "Dallas": "DFW",
    "Detroit": "DTW",
    "Manila": "MNL",
    "Hong Kong": "HKG",
    "Warsaw": "WAW",
    "Frankfurt": "FRA",
    "New York Newark": "EWR",
    "London": "LHR"
};

getCityName = function(id) {
    return Object.keys(citySources).find(key => citySources[key] === id);
};

ac = $('input.autocomplete').autocomplete({
    source: Object.keys(citySources),
    limit: 20, // The max amount of results that can be shown at once. Default: Infinity.
    select: function(e) {
        setTimeout(function(){
            ac.each(function(){
                if($(this).val()) $(this).blur().valid();
            })
        }, 20);
    },
    minLength: 1, // The minimum length of the input for the autocomplete to start. Default: 1.
});

$('.datepicker').pickadate({
    selectMonths: true, // Creates a dropdown to control month
    selectYears: 15, // Creates a dropdown of 15 years to control year,
    today: 'Today',
    clear: 'Clear',
    close: 'Ok',
    closeOnSelect: true, // Close upon selecting a date,
    onClose: function() {
        $('.datepicker').blur();
    }
});

$.validator.addMethod("correctName", function(str){
        res = str.split(" ");
        if (res.length !== 2) return false;
        for (i=0; i<2; i++) {
            if (res[i].length < 3) return false;
            if (res[i][0] !== res[i][0].toUpperCase()) return false;
            for (j=1; j<res[i].length; j++) {
                if (res[i][j] !== res[i][j].toLowerCase()) return false;
            }
            if (!/^[\u0080-\u0290\w]+$/g.test(res[i])) return false;
        }
        return true;
    });

roundTrip = false;

$('input:radio[name="fType"]').change(
    function(){
        if (this.checked && this.value == 'roundTrip') {
            roundTrip = true;
            $("#departureDate").parent().removeClass("m12").addClass("m6", 100);
            $("#returnDate").parent().removeClass("hide", 100).show("slide", { direction: "left" }, 300);
        } else {
            roundTrip = false;
            $("#returnDate").parent().addClass("hide");
            $("#departureDate").parent().removeClass("m6").addClass("m12");
        }
    });

cityValidationMessages = {
    required: "Please insert the name of city.",
    inArray: "There is no such city in our database.",
    notEqual: "Departure and arrival locations should be different."
};

$('#ticket form').submit(function(e) {
    e.preventDefault();
}).validate({
    rules: {
        from: { notEqual: "#to" },
        to: { notEqual: "#from" },
        departureDate: { required: true },
        returnDate: {
            required: {
                depends: function () {
                    return roundTrip;
                }
            }
        },
        fClassHolder: { required: true }
    },
    messages: {
        from: cityValidationMessages,
        to: cityValidationMessages,
        departureDate: { required: "Please insert departure date." },
        returnDate: { required: "Please insert return date." },
        fClassHolder: { required: "Please select the seat class." }
    },

    errorClass: "invalid",
    errorPlacement: function(error, element) {
        $("+ label", element).addClass("active").attr('data-error', error[0].innerText);
    },

    focusInvalid: false,
    invalidHandler: function(form, validator) {
        var errors = validator.numberOfInvalids();
        if (errors) {
            if(!$(validator.errorList[0].element).hasClass("datepicker")) {
                validator.errorList[0].element.focus();
            }
        }
    },

    submitHandler: function(form){
        $("#fromId").val(citySources[$("#from").val()]);
        $("#toId").val(citySources[$("#to").val()]);
        $.ajax({
            type: "POST",
            url: "./api/v1/addTicket",
            data: $(form).serialize(), // serializes the form's elements.
            success: function(data)
            {
                patch_metadata(data);
                Materialize.toast('Successfully added a ticket!', 4000);
            },
            error: function (error) {
                Materialize.toast('Error! '+error, 4000);
            }
        });
    }
});

$.validator.addMethod("inArray", function(str, elem, array){
    return array.includes(str);
});

jQuery.validator.addMethod("notEqual", function(value, element, param) {
    return value != $(param).val();
});

$.validator.addClassRules({
    city: {
        required: true,
        inArray: Object.keys(citySources)
    }
});

$(document).ready(function() {
    window.validate_field = function () {} //stop Materialize from messing with my validation classes
});

$("#fClassHolder").focus(function () {
    this.blur();
});

$(".checkboxes [name='fClass']").change(
    function () {
        $("#fClassHolder").removeClass('invalid').addClass('valid').val(' ');
        $("#fClassHolder + label").removeClass('active');
    }
);
