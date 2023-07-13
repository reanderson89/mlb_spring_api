const playerForm = document.getElementById("player-form");

const createPlayer = async (player) => {
    let response = await fetch("/api/players", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        // JSON.stringify() turns our object into a string
        body: JSON.stringify(player),
    })
    let newPlayer = await response.json()
    return
}

const displayPlayers = async () => {
    
    const playerTable = document.getElementById("player-table")
    playerTable.innerHTML = ""

    let response = await fetch("/api/players")
    let players = await response.json()

    //   forEach is a builtin method for arrays
  players.forEach((player) => {
    // Step1: create element
    let newRow = document.createElement("tr");
    newRow.setAttribute("data-id", player.id)
    // Step2: give it content
    newRow.innerHTML = `
    <td>${player.name}</td>
    <td>${player.age}</td>
    <td>${player.yearsOfExperience}</td>
    <td>${player.rating}</td>`;

    // Step 3: append to parent
    playerTable.append(newRow);
  });
  return
}


playerForm.addEventListener('submit', async function(event) {
    event.preventDefault();

    let formData = new FormData(playerForm);
    let formObject = Object.fromEntries(formData.entries());
    playerForm.reset()
    await createPlayer(formObject);
    await displayPlayers();

});

displayPlayers();