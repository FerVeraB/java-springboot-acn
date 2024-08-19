Vue.createApp({
    data() {
        return {
            clientInfo: {},
            creditCards: [],
            debitCards: [],
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    //get client ifo
                    this.clientInfo = response.data;
                    this.creditCards = this.clientInfo.cards.filter(card => card.type == "CREDIT");
                    this.debitCards = this.clientInfo.cards.filter(card => card.type == "DEBIT");


                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        deleteCard: function (cardId) {
        if (confirm("¿Seguro que desea eliminar la tarjeta?")){
        axios.delete('/api/cards/' + cardId)
                        .then(response => {
                            // Verifica si el estado de la respuesta es OK (200)
                            if (response.status === 200) {
                                // Muestra un mensaje de éxito
                                console.log("Tarjeta eliminada correctamente");
                                // Actualiza los datos de las tarjetas
                                this.getData();
                            } else {
                                // Muestra un mensaje de error
                                console.error("Error al eliminar la tarjeta");
                                this.errorToats.show();
                            }
                        })
                        .catch(error => {
                            // Muestra un mensaje de error
                            console.error("Error al eliminar la tarjeta:", error.message);
                            this.errorToats.show();
                        });
        }

        }

    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();

    }
}).mount('#app')