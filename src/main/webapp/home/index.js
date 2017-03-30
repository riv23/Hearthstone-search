import angular from 'angular';
import uirouter from 'angular-ui-router';

import routing from './home.routes';
import HomeController from './home.controller';
import searchService from '../services/search';
import card from '../directives/card';
// import rarity from '../directives/rarity';
import manaFilter from '../filters/manaFilter';
import trusted from '../filters/htmlFilter';


export default angular.module('app.home', [uirouter, searchService, card])
  .config(routing)
  .controller('HomeController', HomeController)
  .filter({manaFilter})
  .filter({trusted})
  .name;
