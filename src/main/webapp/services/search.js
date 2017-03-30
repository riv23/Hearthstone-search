import angular from 'angular';

class SearchService {
  constructor($http) {
    this.$http = $http;
  }

  search() {
    const API_URI_ROOT = 'https://api.hearthstonejson.com/v1/latest/';
    const LANG= "enUS";
    const URL = API_URI_ROOT + LANG + "/cards.collectible.json";
    return this.$http({method : 'GET', url: URL});
  }
}

export default angular.module('services.search', [])
  .service('searchService', SearchService)
  .name;
