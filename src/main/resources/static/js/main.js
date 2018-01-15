var loaded = 0, toLoad = 2;

function loadStep() {
    loaded++;
    if (loaded >= toLoad) {
        $("#app").attr('loaded', 'true');
        $(".determinate").show("slide", { direction: "left" }, 1000);
        $(".tabs > .tab:first-child > a").click();
    }
}

var application = new Vue({
    el: '#app',
    data: {
        text: "Hello world!"
    },
    mounted() {
        this.$http.get('/api/v1/getUserStatus').then(
            response => {
                application.text = response.body;
                loadStep()
            },
            response => {
                console.log("error");
            }
        )
    }
});

$(function() {
    loadStep();
});