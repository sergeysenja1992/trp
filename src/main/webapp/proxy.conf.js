const PROXY_CONFIG = [
    {
        context: [
            "/logout",
            "/google/login",
            "/api"
        ],
        target: "http://ssenko.pp.ua:8080",
        secure: false,
        changeOrigin: false,
        logLevel: "debug"
    }
];

module.exports = PROXY_CONFIG;
