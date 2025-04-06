// ========== Topbar scroll-effekt ==========
window.addEventListener("scroll", () => {
    const topbar = document.querySelector(".topbar");
    if (!topbar) return;

    if (window.scrollY > 10) {
        topbar.classList.add("scrolled");
    } else {
        topbar.classList.remove("scrolled");
    }
});

// ========== Info-box fade-in ved scroll ==========
const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('visible');
            observer.unobserve(entry.target); // Kun én gang
        }
    });
}, {
    threshold: 0.1
});

document.querySelectorAll('.info-box').forEach(box => observer.observe(box));

// ========== Smooth scroll til sektion ==========
document.querySelector('.arrow').addEventListener('click', function (e) {
    e.preventDefault();

    const target = document.querySelector('#inspiration');
    const start = window.pageYOffset;
    const end = target.getBoundingClientRect().top + start;
    const distance = end - start;
    const duration = 1500;

    let startTime = null;

    function animation(currentTime) {
        if (!startTime) startTime = currentTime;
        const timeElapsed = currentTime - startTime;
        const run = easeInOutQuad(timeElapsed, start, distance, duration);
        window.scrollTo(0, run);
        if (timeElapsed < duration) requestAnimationFrame(animation);
    }

    function easeInOutQuad(t, b, c, d) {
        t /= d / 2;
        return t < 1
            ? c / 2 * t * t + b
            : -c / 2 * (--t * (t - 2) - 1) + b;
    }

    requestAnimationFrame(animation);
});

// ========== Logo-banner – smooth scroll og hover-pause ==========
document.addEventListener('DOMContentLoaded', function () {
    const track = document.getElementById('scroll-track');
    if (!track) return;

    let position = 0;
    let animationFrame;
    let speed = 1.2;
    let targetSpeed = speed;

    function loop() {
        // Smooth hastighedsændring
        speed += (targetSpeed - speed) * 0.08;

        position -= speed;
        if (position <= -track.scrollWidth / 2) {
            position = 0;
        }

        track.style.transform = `translateX(${position}px)`;
        animationFrame = requestAnimationFrame(loop);
    }

    track.addEventListener('mouseenter', () => {
        targetSpeed = 0; // Glidende stop
    });

    track.addEventListener('mouseleave', () => {
        targetSpeed = 1.2; // Glidende start igen
    });

    loop();
});