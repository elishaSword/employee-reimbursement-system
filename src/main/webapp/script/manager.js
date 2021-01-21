'use strict';

document.getElementById('ticketShowerByStatus').addEventListener('click', showTicketsByStatus);

const getterByStatusStatus = document.getElementById('ticketShowerByStatusStatusContainer');

function showTicketsByStatus() {
	getterByStatusStatus.classList.add('hidden');
	
	const status = new FormData(document.getElementById('byStatus'))
		.get('status');
	
	if (status === '') {
		replaceNodeWithText(getterByStatusStatus, 'no tickets retrieved. you must select a status');
	} else {

	fetch(new Request(`/Project1/api/ticket/${status.toLowerCase()}`))
		.then(response => {
			let output = response;

			if (response.ok) {
				replaceNodeWithText(getterByStatusStatus, 'tickets retrieved');
				getterByStatusStatus.classList.remove('hidden');
			} else {
				replaceNodeWithText(getterByStatusStatus, 'failed to retrieve tickets');
				getterByStatusStatus.classList.remove('hidden');
				output = Promise.reject();
			}
			
			return output;
		})
		.then(response => response.json()) 
		.then(placeTicketsByStatus);
	}
}

function placeTicketsByStatus(tickets) {
	const placeholder = document.getElementById('outputContainer');
	
	while (placeholder.lastChild) {
			placeholder.removeChild(placeholder.lastChild);
		}
	
	console.log(tickets);
	for (const t of tickets) {
		placeholder.appendChild(getDlOfTicketByStatus(t));
	}
}

function getDlOfTicketByStatus(ticket) {
	const form = document.createElement('form');
	form.classList.add('container');
	form.classList.add('ticket');

	const dl = document.createElement('dl');
	dl.classList.add('container');
	
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
	
	form.appendChild(dl);
	if (ticket.status.toLowerCase() === 'todo') {
		const approve = document.createElement('approve');
		const deny = document.createElement('deny');
	
		approve.classList.add('form-control');
		approve.classList.add('btn');
		approve.classList.add('btn-info');
		deny.classList.add('form-control');
		deny.classList.add('btn');
		deny.classList.add('btn-info');
		deny.classList.add('negater');

		approve.appendChild(document.createTextNode('accept'));
		deny.appendChild(document.createTextNode('reject'));
	
		const statusContainer = document.createElement('div');
		statusContainer.classList.add('hidden');
	
		approve.addEventListener('click', adjustStatus(ticket, 'accepted', statusContainer));
		deny.addEventListener('click', adjustStatus(ticket, 'rejected', statusContainer));
			
		form.appendChild(approve);
		form.appendChild(deny);
		form.appendChild(statusContainer);
	}
	
	return form;
}

function adjustStatus(ticket, status, statusContainer) {
	return () => {
		console.log(ticket, status);

		statusContainer.classList.add('hidden');

		fetch(new Request(`/Project1/api/ticket/${ticket.id}`,
			{
				method: 'PUT',
				headers: new Headers()
					.append('Content-Type', 'application/json'),
				body: JSON.stringify(Object.assign(ticket,
						{
							'status': status,
						})
					),
			})
		)
			.then(response => {
				if (response.ok) {
					replaceNodeWithText(statusContainer, `ticket ${status}`);
					statusContainer.classList.remove('hidden');
				} else {
					replaceNodeWithText(statusContainer, `ticket not ${status}: unknown failure`);
					statusContainer.classList.remove('hidden');
				}
			
				console.log(response);
			});
	};
}