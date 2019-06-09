: <template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>Welcome Component</p>
    <b-table striped hover :items="spots"></b-table>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from "vue-property-decorator";
import { RestApi } from "../services/RestApi";

@Component
export default class Welcome extends Vue {
  @Prop() private msg!: string;
  //@Prop() private spots!: Array<object>;

  private restApi = new RestApi();

  get spots() {
    this.restApi.getSpots().then((resp: any) => {
      console.log(resp);
    });
    let returnArr = [];
    returnArr.push({ name: "ayaz" });
    return returnArr;
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
