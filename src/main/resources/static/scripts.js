const app = document.getElementById('root');

const logo = document.createElement('img');
// logo.src = 'logo.png';

const container = document.createElement('div');
container.setAttribute('class', 'container');

app.appendChild(logo);
app.appendChild(container);

var request = new XMLHttpRequest();
request.open('GET', 'http://localhost:8080/persons', true);
request.onload = function () {

    // Begin accessing JSON data here
    var data = JSON.parse(this.response);
    console.log(data);
    if (request.status >= 200 && request.status < 400) {
        var i = 0;
        data.forEach(person => {
            const card = document.createElement('div');
            card.setAttribute('class', 'card');

            const h1 = document.createElement('h1');
            h1.textContent = person.name;

            const p = document.createElement('p');
            //person.description = person.description.substring(0, 300);
            //p.textContent = `${person.description}...`;
            p.textContent = person.email;
            p.id = 'email' + i;

            const q = document.createElement('p');
            q.textContent = person.birthDate;
            q.id = 'dob' + i;

            container.appendChild(card);
            card.appendChild(h1);
            card.appendChild(p);
            card.appendChild(q);

            i++;
        });
    } else {
        const errorMessage = document.createElement('marquee');
        errorMessage.textContent = `Gah, it's not working!`;
        app.appendChild(errorMessage);
    }
}

request.send();


(function () {
    var conn = new WebSocket("ws://127.0.0.1:8080/raw");

    conn.onmessage = function (evt) {
        console.log(evt.data);
        //data.innerHTML = data.innerHTML + '<br>' + 'received' + evt.data;
        var data = document.getElementById("email0");
        console.log(data.textContent);
        data.textContent = 'hello';
    }
})();