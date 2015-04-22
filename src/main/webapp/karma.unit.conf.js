module.exports = function(config) {
    config.set({
        basePath: './',
        files: [
            'js/lib/angular.min.js',
            'js/lib/angular-mocks.js',
            'js/**/*.js',
            'js/test/unit/**/*.js'
        ],
        frameworks: ['jasmine'],
        autoWatch: true,
        browsers: ['PhantomJS']
    });
};