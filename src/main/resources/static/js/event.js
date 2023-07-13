// Fetch players and teams on page load
async function init() {

    const playerResponse = await fetch('/api/players');
    const players = await playerResponse.json();
    
    const teamResponse = await fetch('/api/teams');
    const teams = await teamResponse.json();
  
    // Populate player dropdown
    const playerSelect = document.getElementById('player-id');
    players.forEach(player => {
      const option = document.createElement('option');
      option.value = player.id;
      option.text = player.name;
      playerSelect.appendChild(option);
    });
  
    // Populate team dropdown
    // Same as player dropdown
    const teamSelect = document.getElementById('team-id');
    teams.forEach(player => {
      const option = document.createElement('option');
      option.value = player.id;
      option.text = player.name;
      teamSelect.appendChild(option);
    });
  }


const displayEvents = async () => {
const eventsTable = document.getElementById('event-table');
eventsTable.innerHTML = '';
const response = await fetch('/api/events/names');
console.log(response);
const events = await response.json();
console.log(events)
events.forEach(event => {
    const newRow = document.createElement('tr');
    newRow.setAttribute("data-id", event.id)
    newRow.innerHTML = `
    <td>${event.playerName}</td>
    <td>${event.teamName}</td>
    <td>${event.eventType}</td>
    `;
    eventsTable.appendChild(newRow);
    })
}
  
  // Submit form 
  const eventForm = document.getElementById('event-form');
  eventForm.addEventListener('submit', async event => {
  
    event.preventDefault();
    
    // Get data from dropdowns
    let formData = new FormData(eventForm);
    let formObject = Object.fromEntries(formData.entries());
  
    const response = await fetch('/api/events', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formObject)
    });
    // Clear form and display events
    eventForm.reset();

    await displayEvents();

  
  });
  
  init();
  displayEvents();