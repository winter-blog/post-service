package com.devwinter.postservice.adapter.output.persistence.bulk;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import com.devwinter.postservice.adapter.output.persistence.entity.PostImageValueObject;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class PostJpaEntityMockBulkInsertRepositoryImpl implements PostJpaEntityMockBulkInsertRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void bulkInsert(int startIndex, List<PostEntity> posts) {
        String sql =
                "insert into post (created_at, modified_at, deleted, category, contents, title, member_id, member_active) " +
                        "values (?,?,?,?,?,?,?,?)";

        String sql2 =
                "insert into post_image (post_id, order_number, path) " +
                        "values (?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PostEntity post = posts.get(i);
                ps.setTimestamp(1, Timestamp.valueOf(post.getCreatedAt()));
                ps.setTimestamp(2, Timestamp.valueOf(post.getCreatedAt()));
                ps.setBoolean(3, post.isDeleted());
                ps.setString(4, post.getCategory().name());
                ps.setString(5, post.getContents());
                ps.setString(6, post.getTitle());
                ps.setLong(7, post.getMemberId());
                ps.setBoolean(8, post.isMemberActive());
            }

            @Override
            public int getBatchSize() {
                return posts.size();
            }
        });

        jdbcTemplate.batchUpdate(sql2, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PostImageValueObject postImage = posts.get(i).getPostImageCollection().getPostImages().get(0);
                ps.setLong(1, startIndex + i + 1);
                ps.setInt(2, postImage.getOrderNumber());
                ps.setString(3, postImage.getPath());
            }

            @Override
            public int getBatchSize() {
                return posts.size();
            }
        });
    }

    @Override
    public int getMaxPostId() {
        String sql = "select max(post_id) from post";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return Objects.requireNonNullElse(result, 0);
    }
}