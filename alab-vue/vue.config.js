module.exports = {
  transpileDependencies: ["vuetify"],
  devServer: {
    proxy: {
      "^/v1": {
        target: "http://localhost:8180",
        changeOrigin: true
      }
    }
  }
};
