package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Hall;
import com.evil.cbs.domain.Seat;
import com.evil.cbs.domain.SeatStatus;
import com.evil.cbs.repository.SeatRepository;
import com.evil.cbs.service.SeatService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SeatServiceImplTest {

    @Mock
    private SeatRepository seatRepository;
    private SeatService seatService;
    private Hall hall;
    private Seat h44;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        seatService = new SeatServiceImpl(seatRepository);

        hall = Hall.builder()
                .name("Hall 1")
                .build();
        hall.setId(1L);

        h44 = Seat.builder()
                .hall(hall)
                .price(145)
                .seatNumber("H44")
                .seatStatus(SeatStatus.FREE)
                .build();

        when(seatRepository.findBySeatNumber(h44.getSeatNumber())).thenReturn(h44);
        when(seatRepository.findBySeatNumberAndHallId(h44.getSeatNumber(), hall.getId())).thenReturn(h44);
        when(seatRepository.findAllByHallId(hall.getId())).thenReturn(Arrays.asList(h44));
    }

    @Test
    public void testFindBySeatNumber() {
        Seat bySeatNumber = seatService.findBySeatNumber(h44.getSeatNumber());
        verify(seatRepository).findBySeatNumber(h44.getSeatNumber());
        assertThat(bySeatNumber).isEqualTo(h44);
    }

    @Test
    public void testFindBySeatNumberAndHallId() {
        Seat bySeatNumberAndHallId = seatService.findBySeatNumberAndHallId(h44.getSeatNumber(), hall.getId());
        verify(seatRepository).findBySeatNumberAndHallId(h44.getSeatNumber(), hall.getId());
        assertThat(bySeatNumberAndHallId).isEqualTo(h44);
    }

}