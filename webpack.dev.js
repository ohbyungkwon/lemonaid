const path = require('path');
const webpack = require('webpack');
module.exports = {
    devtool: 'inline-source-map',
    devServer: {
        historyApiFallback: true,
        publicPath: '/dist/js',
        host: "0.0.0.0",
        port: 3000,
        proxy: {
            "**": "http://localhost"
        }
    },
    plugins: [
        new webpack.NamedModulesPlugin()
    ]
};