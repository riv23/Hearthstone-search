import 'bootstrap/dist/css/bootstrap.css';
import angular from 'angular';
import uirouter from 'angular-ui-router';

import routing from'./app.config';
import '../style/app.css';
import '../style/autocomplete.css';
import '../style/hssc-bootstrap.css';
import '../style/ngProgress.css';
import '../style/rotating-card.css';
import '../style/toggle-switch.css';

import home from './home';

const MODULE_NAME = 'app';

angular.module(MODULE_NAME, [uirouter, home])
  .config(routing);

export default MODULE_NAME;
