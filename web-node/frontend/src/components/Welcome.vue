: <template>
  <div class="hello">
    <b-table striped hover :items="spots"></b-table>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import { RestApi } from "../services/RestApi";

@Component
export default class Welcome extends Vue {
  @Prop() private msg!: string;
  private listOfSpots: Array<object> = [];
  private restApi = new RestApi();
  private timer: any;

  get spots() {
    return this.listOfSpots;
  }

  created() {
    this.getVonAPI();
    this.timer = setInterval(this.getVonAPI, 10000);
  }

  getVonAPI() {
    this.restApi.getSpots().then((resp: any) => {
      this.listOfSpots = resp["data"].map((el: any) => {
        if (el.status === "FREE") {
          el._rowVariant = "success";
        }
        return el;
      });
    });
  }

  beforeDestroy() {
    clearInterval(this.timer);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
