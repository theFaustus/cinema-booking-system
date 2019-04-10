package com.evil.cbs.service.impl;

import com.evil.cbs.domain.Hall;
import com.evil.cbs.repository.HallRepository;
import com.evil.cbs.service.HallService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HallServiceImplTest {

    @Mock
    private HallRepository hallRepository;
    private HallService hallService;
    private Hall hall;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        hallService = new HallServiceImpl(hallRepository);

        hall = Hall.builder()
                .name("Hall 1")
                .build();
        hall.setId(1L);

        when(hallRepository.findById(hall.getId())).thenReturn(Optional.of(hall));
        when(hallRepository.findByName(hall.getName())).thenReturn(Optional.of(hall));
    }

    @Test
    public void testFindHallByName() {
        Hall hallByName = hallService.findHallByName(hall.getName());
        verify(hallRepository).findByName(hall.getName());
        assertThat(hallByName).isEqualTo(hall);
    }

    @Test
    public void testFindById() {
        Hall hallById = hallService.findById(hall.getId());
        verify(hallRepository).findById(hall.getId());
        assertThat(hallById).isEqualTo(hall);
    }
}