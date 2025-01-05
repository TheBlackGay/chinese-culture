import {
  isEmpty,
  deepClone,
  formatFileSize,
  encrypt,
  decrypt,
  isExternalLink,
  random,
} from '../common';

describe('common utils', () => {
  describe('isEmpty', () => {
    it('should return true for empty values', () => {
      expect(isEmpty(undefined)).toBe(true);
      expect(isEmpty(null)).toBe(true);
      expect(isEmpty('')).toBe(true);
      expect(isEmpty([])).toBe(true);
      expect(isEmpty({})).toBe(true);
    });

    it('should return false for non-empty values', () => {
      expect(isEmpty('test')).toBe(false);
      expect(isEmpty([1, 2, 3])).toBe(false);
      expect(isEmpty({ key: 'value' })).toBe(false);
      expect(isEmpty(0)).toBe(false);
      expect(isEmpty(false)).toBe(false);
    });
  });

  describe('deepClone', () => {
    it('should clone primitive values', () => {
      expect(deepClone(42)).toBe(42);
      expect(deepClone('test')).toBe('test');
      expect(deepClone(true)).toBe(true);
      expect(deepClone(null)).toBe(null);
      expect(deepClone(undefined)).toBe(undefined);
    });

    it('should deep clone objects', () => {
      const obj = {
        a: 1,
        b: { c: 2 },
        d: [1, 2, { e: 3 }],
      };
      const cloned = deepClone(obj);
      expect(cloned).toEqual(obj);
      expect(cloned).not.toBe(obj);
      expect(cloned.b).not.toBe(obj.b);
      expect(cloned.d).not.toBe(obj.d);
    });

    it('should clone Date objects', () => {
      const date = new Date();
      const cloned = deepClone(date);
      expect(cloned).toEqual(date);
      expect(cloned).not.toBe(date);
    });

    it('should clone RegExp objects', () => {
      const regex = /test/gi;
      const cloned = deepClone(regex);
      expect(cloned).toEqual(regex);
      expect(cloned).not.toBe(regex);
    });
  });

  describe('formatFileSize', () => {
    it('should format file sizes correctly', () => {
      expect(formatFileSize(0)).toBe('0 B');
      expect(formatFileSize(1024)).toBe('1 KB');
      expect(formatFileSize(1024 * 1024)).toBe('1 MB');
      expect(formatFileSize(1024 * 1024 * 1024)).toBe('1 GB');
    });
  });

  describe('encrypt and decrypt', () => {
    it('should encrypt and decrypt data correctly', () => {
      const data = 'test data';
      const encrypted = encrypt(data);
      expect(typeof encrypted).toBe('string');
      expect(encrypted).not.toBe(data);
      expect(decrypt(encrypted)).toBe(data);
    });
  });

  describe('isExternalLink', () => {
    it('should identify external links correctly', () => {
      expect(isExternalLink('https://example.com')).toBe(true);
      expect(isExternalLink('http://example.com')).toBe(true);
      expect(isExternalLink('mailto:test@example.com')).toBe(true);
      expect(isExternalLink('tel:1234567890')).toBe(true);
      expect(isExternalLink('/internal/path')).toBe(false);
      expect(isExternalLink('internal/path')).toBe(false);
    });
  });

  describe('random', () => {
    it('should generate random numbers within range', () => {
      const min = 1;
      const max = 10;
      for (let i = 0; i < 100; i++) {
        const result = random(min, max);
        expect(result).toBeGreaterThanOrEqual(min);
        expect(result).toBeLessThanOrEqual(max);
        expect(Number.isInteger(result)).toBe(true);
      }
    });
  });
}); 