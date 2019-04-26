$(() =>
  $('[data-toggle]').on('click', function() {
    const toggle = $(this).addClass('active').attr('data-toggle');
    $(this).siblings('[data-toggle]').removeClass('active');
    return $('.surveys').removeClass('grid list').addClass(toggle);
})
);