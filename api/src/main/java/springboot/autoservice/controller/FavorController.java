package springboot.autoservice.controller;

import springboot.autoservice.dto.request.FavorRequestDto;
import springboot.autoservice.dto.response.FavorResponseDto;
import springboot.autoservice.model.Favor;
import springboot.autoservice.service.FavorService;
import springboot.autoservice.dto.mapper.FavorMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favors")
public class FavorController {
    private final FavorService favorService;
    private final FavorMapper favorMapper;

    public FavorController(FavorService favorService,
                           FavorMapper favorMapper) {
        this.favorService = favorService;
        this.favorMapper = favorMapper;
    }

    @PutMapping("/{id}")
    public FavorResponseDto update(@PathVariable Long id,
                                        @RequestBody FavorRequestDto favorRequestDto) {
        Favor favor = favorMapper.mapToModel(favorRequestDto);
        favor.setId(id);
        return favorMapper.mapToDto(favorService.create(favor));
    }

    @PutMapping("/{id}/status")
    public FavorResponseDto updateStatus(@PathVariable Long id,
                                              @RequestParam String newStatus) {
        return favorMapper.mapToDto((
                        favorService.changeStatus(id, Favor.Status.valueOf(newStatus))));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavorResponseDto create(@RequestBody FavorRequestDto favorRequestDto) {
        return favorMapper
                .mapToDto(favorService
                        .create(favorMapper.mapToModel(favorRequestDto)));
    }
}
