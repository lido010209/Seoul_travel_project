$(window).on("scroll", function () {
  if ($(this).scrollTop() > 600) { // Kiểm tra xem người dùng đã cuộn hơn 50px chưa
    $(".navbar").addClass("scrolled");
    $(".navbar-brand").removeClass("text-white");
  } else {
    $(".navbar").removeClass("scrolled");
    $(".navbar-brand").addClass("text-white");

  }
});

// Khi cuộn trang
window.addEventListener('scroll', function () {
  var elements = document.querySelectorAll('.scroll-block');
  var windowHeight = window.innerHeight;
  elements.forEach(function (element) {
    var elementPosition = element.getBoundingClientRect().top;
    var elementBottomPosition = element.getBoundingClientRect().bottom;

    // Kiểm tra khi phần tử xuất hiện trên màn hình
    if (elementPosition < windowHeight - 100 && elementBottomPosition > 100) {
      element.classList.add('visible');
    } else {
      // Loại bỏ lớp 'visible' khi phần tử ra khỏi màn hình
      element.classList.remove('visible');
    }
  });
});


// const profileButton = document.getElementById('profileButton');
// const dropdownMenu = document.getElementById('profile-dropdown-menu');
// profileButton.addEventListener('click', function () {
//   if (dropdownMenu.style.display == 'block') {
//     dropdownMenu.style.display = 'none';
//   } else {
//     dropdownMenu.style.display = 'block';
//   }
// })

// window.addEventListener('click', function (e) {
//   if (!profileButton.contains(e.target) && !dropdownMenu.contains(e.target)) {
//     dropdownMenu.style.display = 'none';
//   }
// });
