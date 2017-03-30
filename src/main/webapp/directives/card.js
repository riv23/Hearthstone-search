function Card() {
  return {
    restrict: 'E',
    templateUrl: 'directives/card.html'
  }
}

export default angular.module('directives', [])
  .directive('card', Card)
  .name;
