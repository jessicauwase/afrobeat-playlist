const AudioContext = window.AudioContext || window.webkitAudioContext;
const audioContext = new AudioContext();
let audio, audioTag;

let imageClass = document.getElementsByClassName('play-button');

for (let i = 0; i < imageClass.length; i++) {
    imageClass[i].onclick = () => {
        fetchAudio(imageClass[i].dataset.url);
    };
}

const authenticated = document.getElementById("authenticated");
const notAuthenticated = document.getElementById("not-authenticated")

const token = localStorage.getItem("token");

const logout = document.getElementById("logout");
logout.onclick = logoutFn

function logoutFn(){
	localStorage.clear();
	window.location.reload()
}

if(token){
	authenticated.style="display:unset;"
	notAuthenticated.style="display: none"
}else{
	notAuthenticated.style="display:unset;"
	authenticated.style="display: none"
	location.pathname="/login"
}

const share = document.getElementById("share");
share.onclick = function(){
	 window.navigator.clipboard.writeText(location.href);
	 window.alert("Share Link copied successfully")
}



function play() {
    const bufferSource = audioContext.createBufferSource();
    bufferSource.buffer = audio;
    bufferSource.start();
    streamDestination = audioContext.createMediaStreamDestination();
    bufferSource.connect(streamDestination);
    if (typeof audioTag === 'undefined')
        createAndAppendAudioTag(streamDestination);
    else
        changeSrcObject(streamDestination);
}

function createAndAppendAudioTag(streamDestination) {
    audioTag = new Audio();
    audioTag.controls = true;
    audioTag.autoplay = true;
    audioTag.id = 'audioPLayer';
    document.body.appendChild(audioTag);
    audioTag.srcObject = streamDestination.stream;
}

function changeSrcObject(streamDestination) {
    audioTag.srcObject = streamDestination.stream;
}

function fetchAudio(url) {
    fetch(url)
        .then(data => data.arrayBuffer())
        .then(buffer => audioContext.decodeAudioData(buffer))
        .then(decodeAudio => {
            audio = decodeAudio;
        })
        .then(() => {
            play(url);
        })
}

let deleteButton = document.getElementsByClassName("delete-button");

for (let i = 0; i < deleteButton.length; i++) {
    deleteButton[i].onclick = () => {
        deleteAudio(deleteButton[i].dataset.url);
    };
}

function deleteAudio(id) {
    fetch("/delete/" + id, {method: 'DELETE'})
        .then(() => {
            window.location.reload();
        })

}

let editButton = document.getElementsByClassName("edit-button");

for (let i = 0; i < editButton.length; i++) {
    editButton[i].onclick = () => {
        editAudio(editButton[i].dataset.url);
    };
}

function editAudio(id) {
    window.location.href = "/edit/" + id;
}