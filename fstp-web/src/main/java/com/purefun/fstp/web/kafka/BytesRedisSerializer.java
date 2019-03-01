package com.purefun.fstp.web.kafka;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Simple String to byte[] (and back) serializer. Converts Strings into bytes and vice-versa using the specified charset
 * (by default UTF-8).
 * <p>
 * Useful when the interaction with the Redis happens mainly through Strings.
 * <p>
 * Does not perform any null conversion since empty strings are valid keys/values.
 *
 */
public class BytesRedisSerializer implements RedisSerializer<byte[]> {

	private final Charset charset;

	/**
	 * Creates a new {@link BytesRedisSerializer} using {@link StandardCharsets#UTF_8 UTF-8}.
	 */
	public BytesRedisSerializer() {
		this(StandardCharsets.UTF_8);
	}

	/**
	 * Creates a new {@link BytesRedisSerializer} using the given {@link Charset} to encode and decode strings.
	 *
	 * @param charset must not be {@literal null}.
	 */
	public BytesRedisSerializer(Charset charset) {

		Assert.notNull(charset, "Charset must not be null!");
		this.charset = charset;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.serializer.RedisSerializer#deserialize(byte[])
	 */
	@Override
	public byte[] deserialize(@Nullable byte[] bytes) {
		return bytes;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.serializer.RedisSerializer#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(@Nullable byte[] bytes) {
		return bytes;
	}
}
