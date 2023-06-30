function onclickExtendSession() {
    alert("onclickExtendSession()");
}

alert(sessionStorage.getItem('timer'));

setEmailValue();

// updateTimer 함수를 1초마다 호출하는 interval 설정

var timer = parseInt(sessionStorage.getItem('timer')); // timer 값을 가져옴

var expiration = Math.floor(Date.now() / 1000) + timer; // expiration 계산



setInterval(updateTimer, 1000);



// email 값을 세션 스토리지에서 가져와서 span 요소에 설정하는 함수
function setEmailValue() {
  // 세션 스토리지에서 'email' 값 가져오기
  var email = sessionStorage.getItem('email');

  // span 요소 선택
  var spanElement = document.getElementById('login-info');

  // span 요소 내부의 값으로 email 설정
  spanElement.textContent = email;
}



function updateTimer() {

    var countdown = expiration - Math.floor(Date.now() / 1000); // expiration과 현재 시간의 차이를 구함

  // 남은 시간을 'MM:SS' 형식으로 표시하기 위해 분과 초로 변환
  var minutes = Math.floor(countdown / 60);
  var seconds = countdown % 60;

  // 남은 시간을 'MM:SS' 형식으로 포맷팅
  var formattedTime = padNumber(minutes) + ':' + padNumber(seconds);

  // span 엘리먼트에 값 적용
  var sessionTimeElement = document.getElementById('session-time');
  sessionTimeElement.textContent = formattedTime;

  // 1초마다 updateTimer 함수 호출
  if (countdown <= 0) {
    // 타이머가 종료되었을 때의 처리
    clearInterval(timerInterval);
    alert('타이머 종료');
  }
}

function padNumber(number, length) {
  var str = number.toString();
  while (str.length < length) {
    str = "0" + str;
  }
  return str;
}