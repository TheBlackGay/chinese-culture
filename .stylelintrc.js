module.exports = {
  extends: ['stylelint-config-standard'],
  rules: {
    'at-rule-no-unknown': [
      true,
      {
        ignoreAtRules: [
          'tailwind',
          'apply',
          'variants',
          'responsive',
          'screen',
          'layer',
        ],
      },
    ],
    'import-notation': null,
    'keyframe-selector-notation': null,
    'lightness-notation': null,
    'media-feature-range-notation': null,
    'annotation-no-unknown': null,
    'media-query-no-invalid': null,
    'selector-anb-no-unmatchable': null,
    'declaration-block-no-duplicate-properties': null,
  },
};
