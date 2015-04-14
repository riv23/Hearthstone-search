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
        GITHUB : "GitHub page, pull requests are welcome :)"
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
        GITHUB : "Page GitHub, vos pull requests sont les bienvenues :)"
    });

    var defaultLanguage = function () {
        switch (navigator.language) {
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
