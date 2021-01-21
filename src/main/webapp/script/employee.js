'use strict';

let thisEmployeeUsername;
	
fetch(new Request('/Project1/api/employee'))
	.then(response => response.json())
	.then(username => {
		const placeholder = document.getElementById('usernamePlaceholder');
		const form = document.getElementById('createTicket');
		const formInput = document.getElementById('employeeUsername');
		const placeholderParent = placeholder.parentNode;
			
		while (placeholder.lastChild) {
			placeholder.removeChild(placeholder.lastChild);
		}
		placeholderParent.removeChild(placeholder);

		thisEmployeeUsername = username;
		placeholderParent.appendChild(document.createTextNode(username));
		formInput.setAttribute('value', username);
		document.querySelector('main').classList.remove('hidden');
	});

document.querySelectorAll('form')
	.forEach(f => f.addEventListener('submit', e => e.preventDefault()));

document.getElementById('ticketSubmitter').addEventListener('click', deliverTicketViaFetch);
document.getElementById('ticketShower').addEventListener('click', showTickets);

function deliverTicketViaFetch(e) {
	const output = {};
	const form = new FormData(document.querySelector('form'));
	const formStatus = document.getElementById('ticketStatusContainer');
	
	formStatus.classList.add('hidden');

	for (const k of form.keys()) {
		const value = form.get(k);
		if (value === '') {
			replaceNodeWithText(formStatus, 'ticket not submitted. you must fill out every field');
			formStatus.classList.remove('hidden');
			return;
		}
	
		output[k] = value;
	}
	
	fetch(new Request('/Project1/api/ticket',
		{
			method: 'POST',
			headers: new Headers()
				.append('Content-Type', 'application/json'),
			body: JSON.stringify(output),
		})
	)
		.then(response => {
			if (response.ok) {
				replaceNodeWithText(formStatus, 'ticket submitted');
				formStatus.classList.remove('hidden');
			}
			
			console.log(response);
		});
}

const getterStatus = document.getElementById('ticketShowerStatusContainer');

function showTickets() {
	getterStatus.classList.add('hidden');

	fetch(new Request('/Project1/api/ticket/employee'))
		.then(response => {
			let output = response;

			if (response.ok) {
				while (getterStatus.lastChild) {
					getterStatus.removeChild(getterStatus.lastChild);
				}
				replaceNodeWithText(getterStatus, 'tickets retrieved');
				getterStatus.classList.remove('hidden');
			} else {
				replaceNodeWithText(getterStatus, 'failed to retrieve tickets');
				getterStatus.classList.remove('hidden');
				output = Promise.reject();
			}
			
			return output;
		})
		.then(response => response.json())
		.then(placeTickets);
}

function placeTickets(tickets) {
	const placeholder = document.getElementById('outputContainer');
	
	while (placeholder.lastChild) {
			placeholder.removeChild(placeholder.lastChild);
		}
	
	console.log(tickets);
	for (const t of tickets) {
		placeholder.appendChild(getDlOfTicket(t));
	}
}

function getDlOfTicket(ticket) {
	const dl = document.createElement('dl');
	dl.classList.add('ticket');
	
	for (const property of ['employeeUsername', 'type', 'description', 'price', 'timestamp', 'status']) {
		if (ticket.hasOwnProperty(property)) {
			const dt = document.createElement('dt');
			const dd = document.createElement('dd');
			const contents = ticket[property];
			
			dt.appendChild(document.createTextNode(property));
			dd.appendChild(document.createTextNode(property === 'price'
				? formatPrice(contents.toString())
					: property === 'timestamp'
						? new Date(contents).toGMTString()
							: contents));
			
			dl.appendChild(dt);
			dl.appendChild(dd);
		}
	}
	
	return dl;
}

// assumption: price is a number
function formatPrice(priceString) {
	const formattedPriceString = priceString.length >= 3
		? priceString
			: priceString.length == 2
				? `0${priceString}`
					: priceString.length == 1
						? `00${priceString}`
							: '000';

	return `$${formattedPriceString.slice(0, -2)}.${formattedPriceString.slice(-2)}`;
}

function replaceNodeWithText(node, string) {
	while (node.lastChild) {
		node.removeChild(node.lastChild);
	}
	node.appendChild(document.createTextNode(string));
}