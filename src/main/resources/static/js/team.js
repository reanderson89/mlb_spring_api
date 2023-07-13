const teamForm = document.getElementById("team-form");
const teamTable = document.getElementById("team-table");

const createTeam = async (team) => {
  let response = await fetch("/api/teams", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(team)
  });
  console.log(team);
  let newTeam = await response.json();
}

const displayTeams = async () => {
  teamTable.innerHTML = "";

  let response = await fetch("/api/teams");
  let teams = await response.json();

  teams.forEach(team => {
    let newRow = document.createElement("tr");
    newRow.setAttribute("data-id", team.id)
    newRow.innerHTML = `
      <td>${team.name}</td>
      <td>${team.location}</td>
    `;
    
    teamTable.appendChild(newRow);
  });
}


teamForm.addEventListener("submit", async event => {
  event.preventDefault();
  
  let formData = new FormData(teamForm);
  let team = Object.fromEntries(formData);
  teamForm.reset()
  await createTeam(team);
  await displayTeams(); 
});

displayTeams();
