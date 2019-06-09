<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <!-- <HelloWorld msg="Welcome to Your Vue.js + TypeScript App"/> -->
    <Welcome msg="Welcome Component"/>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src
import Welcome from '@/components/Welcome.vue'; // @ is an alias to /src

@Component({
  components: {
    HelloWorld,
    Welcome
  },
})
export default class Home extends Vue {}
</script>
