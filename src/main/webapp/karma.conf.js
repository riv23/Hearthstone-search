module.exports = function(config) {
    config.set({
        basePath: './',
        files: [
            'bower_components/angular/angular.js',
            'bower_components/angular-mocks/angular-mocks.js',
            'bower_components/ngInfiniteScroll/build/ng-infinite-scroll.js',
            'bower_components/angular-translate/angular-translate.js',
            'bower_components/angular-bootstrap/ui-bootstrap.js',
            'bower_components/ngprogress/build/ngprogress.js',
            'bower_components/angular-sanitize/angular-sanitize.js',
            'js/**/*.js',
            'js/test/unit/**/*.js'
        ],
        frameworks: ['jasmine'],
        autoWatch: true,
        browsers: ['PhantomJS'],

        plugins: [
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-phantomjs-launcher',
            'karma-jasmine',
            'karma-junit-reporter',
            'karma-coverage'
        ]
    });
};