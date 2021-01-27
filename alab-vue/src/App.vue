<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
      <v-toolbar-title>ARSlab</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn href="http://arssys/web/main.html" target="_blank" text>
        <span class="mr-2">Location</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <Macaddresslist />
    </v-main>
  </v-app>
</template>

<script>
import Macaddresslist from "./components/MacaddressList";

export default {
  name: "App",

  components: {
    Macaddresslist
  },

  data: () => ({
    //
  })
};
</script>
<style>
/*
@import url("https://fonts.googleapis.com/css2?family=Hachi+Maru+Pop&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Yusei+Magic&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Potta+One&family=Yusei+Magic&display=swap");
@import url("https://fonts.googleapis.com/css2?family=M+PLUS+1p&display=swap");
*/
@import url("https://fonts.googleapis.com/css2?family=Kosugi+Maru&display=swap");
#app {
  /*
  font-family: "Hachi Maru Pop", cursive;
  font-family: "Yusei Magic", sans-serif;
  font-family: "Potta One", cursive;
  font-family: "M PLUS 1p", sans-serif;
  */
  font-family: "Kosugi Maru", sans-serif;
}
</style>
