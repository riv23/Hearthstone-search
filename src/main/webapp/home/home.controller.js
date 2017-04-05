export default class HomeController {

  constructor(searchService, $filter) {
    this.cards = [];
    this.filteredCards = [];
    this.searchService = searchService;
    this.filter = {};
    this.filteredCardsFilter = $filter('manaFilter');
    this.init();
  }

  init() {
    this.searchService.search().then((response) => {
      this.cards = response.data;
      for(let card of this.cards) {
        card.image = "https://art.hearthstonejson.com/v1/render/latest/enUS/256x/"+card.id+".png";
          if(!card.playerClass) {
            card.playerClass = "ALL";
          }
      }

      this.cards = this.cards.filter(card => card.type !== 'HERO')
      this.cards.sort((a, b) => a.cost - b.cost);
    });
  }

  submit() {
     this.filteredCards = this.filteredCardsFilter(this.cards, this.filter)
  }

  filterMana(value) {
    this.filter.cost = value;
    this.filteredCards = this.filteredCardsFilter(this.cards, this.filter)
  }

}
HomeController.$inject = ["searchService", "$filter"];
