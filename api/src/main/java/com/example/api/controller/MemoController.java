package com.example.api.controller;

import com.example.api.dto.MemoRequestDto;
import com.example.api.dto.MemoResponseDto;
import com.example.api.service.MemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/memos")
@RequiredArgsConstructor
public class MemoController {
    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto requestDto) {
        MemoResponseDto memo = memoService.createMemo(requestDto);
        return ResponseEntity
                .created(URI.create("/api/memos/" + memo.getId()))
                .body(memo);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> getAllMemos() {
        return ResponseEntity.ok(memoService.getAllMemos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> getMemo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(memoService.getMemo(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        try {
            memoService.deleteMemo(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 