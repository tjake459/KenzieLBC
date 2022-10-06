import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
//import ExampleClient from "../api/exampleClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class MainPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onGet', 'onCreate', 'removeItem'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('remove-item').addEventListener('submit', this.removeItem);
        document.getElementById('add-new-item').addEventListener('submit', this.onCreate);
        this.client = new ExampleClient();

        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

//    async renderExample() {
//        let resultArea = document.getElementById("result-info");
//
//        const example = this.dataStore.get("example");
//
//        if (example) {
//            resultArea.innerHTML = `
//                <div>ID: ${example.id}</div>
//                <div>Name: ${example.name}</div>
//            `
//        } else {
//            resultArea.innerHTML = "No Item";
//        }
//    }

    // Event Handlers --------------------------------------------------------------------------------------------------

//    async onGet(event) {
//        // Prevent the page from refreshing on form submit
//        event.preventDefault();
//
//        let id = document.getElementById("id-field").value;
//        this.dataStore.set("example", null);
//
//        let result = await this.client.getExample(id, this.errorHandler);
//        this.dataStore.set("example", result);
//        if (result) {
//            this.showMessage(`Got ${result.name}!`)
//        } else {
//            this.errorHandler("Error doing GET!  Try again...");
//        }
//    }

    async removeItem(event) {
        event.preventDefault();

        let itemId = document.getElementById("itemId");
    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        //this.dataStore.set("example", null);

        let genericName = document.getElementById("genericName").value;
        let brandName = document.getElementById("brandName").value;
        let weight = document.getElementById("weight").value;
        let expiration = document.getElementById("expirationDate").value;
        let fillLevel = document.getElementById("fillLevel").value;
        let location = document.getElementById("location").value;

        const newItem = await this.client.addItem(name, this.errorHandler);
        this.dataStore.set("item", newItem);

        if (newItem) {
            this.showMessage(`Created ${newItem.genericName}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const mainPage = new MainPage();
    mainPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
