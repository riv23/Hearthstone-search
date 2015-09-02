app.config(function ($translateProvider) {
    $translateProvider.translations('en', {
        CLASS: 'Class',
        TYPE: 'Type',
        FACTION: 'Faction',
        CITATION: 'Citation',
        RARITY : "Rarity",
        LEGAL : "Card names and text are all copyright Blizzard Entertainment. This website is not affiliated with Blizzard Entertainment in any way.",
        EXPANSION : "Expansion",
        PLACEHOLDER : "Ex : Leeroy Jenkins, Blackrock legendary, ...",
        GITHUB : "GitHub page, pull requests are welcome :)",
        CARDS : "Card(s)",
        FEEDBACK : "Feedback",
        NAME : "Name",
        YOUR_NAME : "Your name",
        EMAIL_ADDRESS : "Email address",
        YOUR_EMAIL_ADDRESS : "Your email address",
        MESSAGE : "Message",
        SEND : "Send",
        CANCEL : "Cancel",
        FEEDBACK_TITLE : "Any comments, any ideas ? You are welcome.",
        HOWTO : "Use keywords. Ex: Leeroy Jenkins, Mage, Hunter Legendary, Grand Tournament Legenday, Mage Spell ..."
    });
    $translateProvider.translations('fr', {
        CLASS: 'Classe',
        TYPE: 'Type',
        FACTION: 'Faction',
        CITATION: 'Citation',
        RARITY : "Rareté",
        LEGAL : "Les noms et les textes des cartes sont la propriété de Blizzard Entertainment. Ce site n'est pas affilié à Blizzard Enternainment en aucun cas.",
        EXPANSION : "Extension",
        PLACEHOLDER : "Ex : Leeroy Jenkins, Blackrock légendaire, ...",
        GITHUB : "Page GitHub, vos pull requests sont les bienvenues :)",
        CARDS : "Carte(s)",
        FEEDBACK : "Avis",
        NAME : "Nom",
        YOUR_NAME : "Votre nom",
        EMAIL_ADDRESS : "Adresse mail",
        YOUR_EMAIL_ADDRESS : "Votre adresse mail",
        MESSAGE : "Message",
        SEND : "Envoyer",
        CANCEL : "Annuler",
        FEEDBACK_TITLE : "Des remarques, des idées ? Vous êtes le/la bienvenue.",
        HOWTO : "Utilisez des mots clés. Ex: Leeroy Jenkins, Mage, Hunter Legendary, Grand Tournament Legenday, Mage Spell ..."
    });

    var defaultLanguage = function () {
        var language = "en";
        if (window.navigator.userAgent.indexOf("MSIE") > 0) {
            language = clientInformation.userLanguage.split("-")[0];
        }else {
            language = navigator.language.split("-")[0];
        }
        switch (language) {
            case "fr" :
                return "fr";
            case "en" :
                return "en";
            default :
                return "en"
        }
    };

    $translateProvider.preferredLanguage(defaultLanguage());
});
