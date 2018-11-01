const path = require('path');
const webpack = require('webpack');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');
module.exports = {
    plugins: [
        new webpack.LoaderOptionsPlugin({
            minimize: true,
            debug: false
        }),
        new UglifyJsPlugin({
            parallel: 4,
            uglifyOptions: {
                ie8: true,
                ecma: 5,
                mangle: {keep_fnames:true}
            }
        })
    ]
};