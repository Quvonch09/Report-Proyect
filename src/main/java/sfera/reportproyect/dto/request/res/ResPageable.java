package sfera.reportproyect.dto.request.res;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResPageable {
    private int page;
    private int size;
    private int totalPage;
    private long totalElements;
    private Object body;
}
