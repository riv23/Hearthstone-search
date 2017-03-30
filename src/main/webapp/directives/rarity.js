class Rarity {
  constructor() {
    this.restrict = 'E';
    this.replace = true;
  }

  link(scope, element, attrs) {
    let value = attrs.value;
    if(value === 'FREE') {
      value = "COMMON";
    }
    let color = "img/" + value.toLowerCase() + ".png";
    element.replaceWith("<img class='img-responsive centerfy ' src='" + color + "' />");
  }
}

export default angular.module('directives', [])
  .directive('rarity', Rarity)
  .name;
