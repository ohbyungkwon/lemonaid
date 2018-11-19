const webpack = require('webpack');
const path = require('path');
const webpackMerge = require('webpack-merge');
const CommonsChunkPlugin = require("webpack/lib/optimize/CommonsChunkPlugin");
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const config = {
    entry: {
        vendor: ['jquery'],
        // bootstrap: path.resolve(__dirname,"src/main/resources/bootstrap/app.js"),

        // semantic :  path.resolve(__dirname,"src/main/resources/templates/semantic/dist/semantic.css"),
        // semanticjs : path.resolve(__dirname, 'src/main/resources/templates/semantic/dist/semantic.js'),

        question : path.resolve(__dirname,"src/main/resources/templates/static/css/question.css"),
        // nonLoginUser : path.resolve(__dirname,"src/main/resources/templates/static/css/nonLoginUser.css"),
        // orderTemp : path.resolve(__dirname,"src/main/resources/templates/static/css/order.css"),
        login: path.resolve(__dirname, "src/main/resources/templates/static/css/login.css"),
        cash: path.resolve(__dirname, "src/main/resources/templates/static/css/cash.css"),
        signInBasic : path.resolve(__dirname, "src/main/resources/templates/static/css/signInBasic.css"),
        signInSpec : path.resolve(__dirname, "src/main/resources/templates/static/css/signInSpec.css"),

        survey: path.resolve(__dirname,"src/main/resources/templates/static/js/SurveyFunction.js"),
        loginForm: path.resolve(__dirname, "src/main/resources/templates/static/js/Login.js")
    },
    output: {
        path: path.resolve(__dirname, 'src/main/resources/static/dist/js'),
        filename: '[name].js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            ['es2015', {modules: false}]
                        ]
                    }
                }]
            },
            {
                test:/\.css$/,
                use : ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: 'css-loader'
                }),
            },
            {
                test: /\.(scss)$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: [
                        {
                            loader: 'css-loader', // translates CSS into CommonJS modules
                        }, {
                            loader: 'postcss-loader', // Run post css actions
                            options: {
                                plugins() {
                                    // post css plugins, can be exported to postcss.config.js
                                    return [
                                        require('autoprefixer')
                                    ];
                                }
                            }
                        }, {
                            loader: 'sass-loader' // compiles SASS to CSS
                        }
                    ]
                })
            },
            {
                test:/\.vue$/,
                use: 'vue-loader'
            },
            //이미지 처리
            {
                test: /\.jpe?g$|\.gif$|\.ico$|\.png$|\.svg$/,
                use: 'file-loader?name=[name].[ext]?[hash]'
            },
            // 폰트 처리
            {
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'url-loader?limit=10000&mimetype=application/font-woff'
            },

            {
                test: /\.(ttf|eot)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'file-loader'
            },
            {
                test: /\.otf(\?.*)?$/,
                use: 'file-loader?name=/fonts/[name].  [ext]&mimetype=application/font-otf'
            },
            { test: /bootstrap\/dist\/js\/umd\//, use: 'imports-loader?jQuery=jquery' },

            // Bootstrap 3
            { test: /bootstrap-sass\/assets\/javascripts\//, use: 'imports-loader?jQuery=jquery' },
        ]
    },
    plugins : [
        // 지정한 변수명으로 라이브러리를 사용할 수 있게됨.
        new webpack.ProvidePlugin({
            $ : "jquery",
            jQuery : "jquery",
            'window.jQuery': 'jquery',
            tether: 'tether',
            Tether: 'tether',
            'window.Tether': 'tether',
            Popper: ['popper.js', 'default'],
            'window.Tether': 'tether',
            Alert: 'exports-loader?Alert!bootstrap/js/dist/alert',
            Button: 'exports-loader?Button!bootstrap/js/dist/button',
            Carousel: 'exports-loader?Carousel!bootstrap/js/dist/carousel',
            Collapse: 'exports-loader?Collapse!bootstrap/js/dist/collapse',
            Dropdown: 'exports-loader?Dropdown!bootstrap/js/dist/dropdown',
            Modal: 'exports-loader?Modal!bootstrap/js/dist/modal',
            Popover: 'exports-loader?Popover!bootstrap/js/dist/popover',
            Scrollspy: 'exports-loader?Scrollspy!bootstrap/js/dist/scrollspy',
            Tab: 'exports-loader?Tab!bootstrap/js/dist/tab',
            Tooltip: "exports-loader?Tooltip!bootstrap/js/dist/tooltip",
            Util: 'exports-loader?Util!bootstrap/js/dist/util'
        }),
        // 공통모듈이 있으면 공통 번들로 추가
        new CommonsChunkPlugin({
            name: "vendor"
        }),
        // Create CSS file
        new ExtractTextPlugin({
            filename: "[name].css",
        })
    ]
};

module.exports = function(env) {
    return webpackMerge(config, require(`./webpack.${env}.js`))
};