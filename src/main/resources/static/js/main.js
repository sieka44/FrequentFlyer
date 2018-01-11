var application = new Vue({
    el: '#app',
    data: {
        text: "Hello world!"
    },
    mounted() {
        this.$http.get('/api/v1/getUserStatus').then(
            response => {
                console.log(response);
                application.text = response.body;
            },
            response => {
                console.log("error");
            }
        )
    }
});
