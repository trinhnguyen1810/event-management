$(document).ready(function () {
    const rsvpButton = $("#rsvp-button");
    const eventId = rsvpButton.attr("data-event-id");
    const initialRsvped = rsvpButton.attr("data-rsvped") === "true";

    rsvpButton.on("click", function () {
        const isRsvped = rsvpButton.hasClass("rsvped");

        if (isRsvped) {
            removeAttendee(eventId);
        } else {
            addAttendee(eventId);
        }

        toggleRsvpButton(rsvpButton, !isRsvped);
    });

    toggleRsvpButton(rsvpButton, initialRsvped);

    function toggleRsvpButton(button, rsvped) {
        button.toggleClass("rsvped", rsvped);
        button.text(rsvped ? "RSVPed!" : "RSVP");
    }

    function addAttendee(eventId) {
        $.post(`/addAttendee?eventId=${eventId}`, function (data) {
            console.log(data);
        });
    }

    function removeAttendee(eventId) {
        $.post(`/removeAttendee?eventId=${eventId}`, function (data) {
            console.log(data);
        });
    }
});
