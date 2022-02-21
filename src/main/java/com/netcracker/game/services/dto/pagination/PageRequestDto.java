package com.netcracker.game.services.dto.pagination;

import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PageRequestDto {

    private Sort sort;
    private Integer page;
    private Integer itemsPerPage;

    public PageRequestDto(String sort, Integer page, Integer itemsPerPage) {
        if (sort != null) {
            switch (sort) {
                case "name":
                    this.sort = Sort.by("name");
                    break;
                case "owner":
                    this.sort = Sort.by("gameLeader.login");
                    break;
                case "type":
                    this.sort = Sort.by("roomType");
                    break;
                case "players":
                    this.sort = Sort.by("roomPlayers.size");
                    break;
            }
        }

        this.page = page;
        this.itemsPerPage = itemsPerPage;
    }

    public Sort getSort() {
        return sort;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageRequestDto that = (PageRequestDto) o;
        return Objects.equals(sort, that.sort) &&
                Objects.equals(page, that.page) &&
                Objects.equals(itemsPerPage, that.itemsPerPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sort, page, itemsPerPage);
    }

    @Override
    public String toString() {
        return "PageRequestDto{" +
                "sort=" + sort +
                ", page=" + page +
                ", itemsPerPage=" + itemsPerPage +
                '}';
    }
}
