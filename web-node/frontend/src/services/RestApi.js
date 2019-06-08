import axios from 'axios';

export class RestApi{

    hostname = "localhost";
    portNr = 8080;

    server = null;

    initialize(params) {
        this.server = axios.create({
            baseURL: 'https://'+ this.hostname + ':'+ this.portNr +'/',
            timeout: 1000,
            headers: {'X-Custom-Header': 'foobar'}
          });
    }

    getAvailableSpots(){
        return this.axios.get('spot/');
    }

}