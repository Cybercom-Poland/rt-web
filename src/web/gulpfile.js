const gulp = require('gulp');
const browserify = require('browserify');
const babelify = require('babelify');
const source = require('vinyl-source-stream');
const jasmine = require('gulp-jasmine');
const uglify = require('gulp-uglify');
var sourcemaps = require('gulp-sourcemaps');
var buffer = require('vinyl-buffer');


gulp.task('dist', function() {
    return browserify({
        entries: 'js/rt-web.js',
        extensions: ['.js']
    })
        .transform('babelify', {presets: ['es2015']})
        .bundle()
        .on('error', function(err) {
            console.error(err);
            this.emit('end');
        })
        .pipe(source('rt-web.min.js'))
        .pipe(buffer())
        .pipe(sourcemaps.init({loadMaps: true}))
        // capture sourcemaps from transforms
        .pipe(uglify())
        .pipe(gulp.dest('dist'));
});

gulp.task('build', function() {
    return browserify({
        entries: 'js/rt-web.js',
        extensions: ['.js'],
        debug: true
    })
        .transform('babelify', {presets: ['es2015']})
        .bundle()
        .on('error', function(err) {
            console.error(err);
            this.emit('end');
        })
        .pipe(source('rt-web.js'))
        .pipe(gulp.dest('dist'));
});

gulp.task('watch', ['build'], function() {
    gulp.watch(['js/*.js'], ['build']);
});

gulp.task('tests', function() {
    var filesForTest = ['dist/bundle.js', 'spec/modules/*.spec.js'];
    return gulp.src(filesForTest)
        .pipe(jasmine());
});