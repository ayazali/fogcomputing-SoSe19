import axios from 'axios';

export class RestApi {

  private server: any;
  private hostname = 'localhost';
  private port = 8080;


  constructor() {
    this.server = axios.create({
      baseURL: `http://${this.hostname}:${this.port}`,
      timeout: 1000,
//      headers: { 'X-Custom-Header': 'foobar' },
    });
  }

  public getSpots() {
    return this.server.get('/spot');
  }
}
